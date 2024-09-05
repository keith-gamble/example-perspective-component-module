# Comprehensive CI/CD Setup Guide: Building, Signing, and Releasing the Example Component Library

This guide explains the Continuous Integration and Continuous Deployment (CI/CD) setup for the Example Component Library using GitHub Actions. It covers the entire process from setting up signing certificates to automating builds and releases.

## Table of Contents
1. [Workflow Overview](#workflow-overview)
2. [Pull Request Builds](#pull-request-builds-packageyaml)
3. [Release Process](#release-process-releaseyaml)
4. [Setting Up GitHub Secrets](#setting-up-github-secrets)
5. [Creating Certificates and KeyStores](#creating-certificates-and-keystores)
6. [Using the CI/CD Pipeline](#using-the-cicd-pipeline)
7. [Best Practices](#best-practices)
8. [Troubleshooting](#troubleshooting)

## Workflow Overview

1. **Pull Request Builds** (`package.yaml`): Builds the module for every pull request to ensure code quality.
2. **Release Process** (`release.yaml`): Builds, signs, and publishes a new release when a version tag is pushed or manually triggered.

## Pull Request Builds (package.yaml)

This workflow runs on every pull request to ensure that the code builds successfully.

### Workflow File: `.github/workflows/package.yaml`

```yaml
name: Build PRs
on: pull_request
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: 'zulu'
          java-version: 11
          cache: 'gradle'
      - name: Build
        run: ./gradlew build
      - name: Upload Unsigned Module
        uses: actions/upload-artifact@v3
        with:
          name: example-library-unsigned
          path: build/Example-Component-Library.unsigned.modl
```

### What it does:

1. Checks out the code
2. Sets up Java 11 (Zulu distribution)
3. Builds the project using Gradle
4. Uploads the unsigned `.modl` file as an artifact

## Release Process (release.yaml)

This workflow is triggered when a version tag is pushed or manually initiated. It builds, signs, and publishes a new release.

### Workflow File: `.github/workflows/release.yaml`

```yaml
name: Publish new version upon tag commit
on:
  push:
    tags:
      - '[0-9].[0-9].[0-9]'
  workflow_dispatch:
    inputs:
      tag:
        description: 'Tag to build and release'
        required: true

jobs:
  build:
    name: Build & Release
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: 'zulu'
          java-version: 11
          cache: 'gradle'
      - name: Deserialize signing certs
        run: |
          echo "${{ secrets.CODE_SIGNING_CERT_BASE64 }}" | base64 --decode > cert.crt
          echo "${{ secrets.CODE_SIGNING_KEYSTORE_BASE64 }}" | base64 --decode > keystore.jks
      - name: Build & create signed module
        run: >
          ./gradlew
          -Pversion=${{ github.event.inputs.tag || github.ref_name }}
          -PsignModule=true
          build
          signModule 
          --certFile=cert.crt
          --certPassword="${{ secrets.CODE_SIGNING_CERT_PASSWORD }}"
          --keystoreFile=keystore.jks
          --keystorePassword="${{ secrets.CODE_SIGNING_KEYSTORE_PASSWORD }}"
          --certAlias="${{ secrets.CODE_SIGNING_CERT_ALIAS }}"
      - name: Create release
        uses: marvinpinto/action-automatic-releases@latest
        with:
          repo_token: ${{ secrets.GITHUB_TOKEN }}
          automatic_release_tag: ${{ github.event.inputs.tag || github.ref_name }}
          prerelease: false
          files: |
            build/Example-Component-Library.modl
            LICENSE.txt
```

### What it does:

1. Triggers on version tag pushes or manual dispatch
2. Sets up Java 11 (Zulu distribution)
3. Deserializes the signing certificate and keystore
4. Builds and signs the module using Gradle
5. Creates a GitHub release with the signed `.modl` file and LICENSE.txt

## Setting Up GitHub Secrets

For the release workflow to function correctly, you need to set up several GitHub secrets:

1. `CODE_SIGNING_CERT_BASE64`: Base64-encoded signing certificate
2. `CODE_SIGNING_KEYSTORE_BASE64`: Base64-encoded keystore file
3. `CODE_SIGNING_CERT_PASSWORD`: Password for the signing certificate
4. `CODE_SIGNING_KEYSTORE_PASSWORD`: Password for the keystore
5. `CODE_SIGNING_CERT_ALIAS`: Alias for the certificate in the keystore

To add these secrets:

1. Go to your GitHub repository
2. Click on "Settings" > "Secrets and variables" > "Actions"
3. Click on "New repository secret"
4. Enter the name and value for each secret
5. Click "Add secret"

## Creating Certificates and KeyStores

Before you can sign your module, you need to create a certificate (.crt) and a Java KeyStore (.jks) file.

### Creating a Self-Signed Certificate and KeyStore

For development and testing purposes, you can create a self-signed certificate:

1. Generate a KeyStore with a new key pair:

   ```bash
   keytool -genkeypair -alias myalias -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650
   ```

   - Replace `myalias` with your desired alias name.
   - This creates `keystore.jks` with a validity of 10 years.

2. Export the certificate from the KeyStore:

   ```bash
   keytool -exportcert -alias myalias -file mycert.crt -keystore keystore.jks
   ```

### Obtaining a CA-Signed Certificate

For production use, obtain a certificate from a trusted Certificate Authority (CA):

1. Generate a Certificate Signing Request (CSR):

   ```bash
   keytool -certreq -alias myalias -file mycsr.csr -keystore keystore.jks
   ```

2. Submit the CSR to your chosen CA and follow their process.

3. Import the signed certificate into your KeyStore:

   ```bash
   keytool -importcert -alias myalias -file signed_cert.crt -keystore keystore.jks
   ```

### Preparing Files for GitHub Secrets

After creating your KeyStore and certificate, encode them for use in GitHub secrets:

1. Base64 encode the KeyStore:

   ```bash
   base64 -w 0 keystore.jks > keystore.base64
   ```

2. Base64 encode the certificate:

   ```bash
   base64 -w 0 mycert.crt > cert.base64
   ```

3. Use the contents of `keystore.base64` for the `CODE_SIGNING_KEYSTORE_BASE64` secret and `cert.base64` for the `CODE_SIGNING_CERT_BASE64` secret in GitHub.

## Using the CI/CD Pipeline

### For Pull Requests:
1. Create a pull request to the main branch
2. The `package.yaml` workflow will automatically run, building the module
3. Check the workflow results to ensure the build is successful

### For Releases:
1. Update the version number in your `build.gradle.kts` file
2. Commit and push your changes
3. Create and push a new tag matching the pattern `[0-9].[0-9].[0-9]`, e.g.:
   ```
   git tag 1.0.0
   git push origin 1.0.0
   ```
4. The `release.yaml` workflow will trigger automatically

Alternatively, you can manually trigger the release workflow:
1. Go to the "Actions" tab in your GitHub repository
2. Select the "Publish new version upon tag commit" workflow
3. Click "Run workflow"
4. Enter the desired tag/version number
5. Click "Run workflow"

## Best Practices

1. Always test your changes locally before creating a pull request
2. Use semantic versioning for your tags (e.g., 1.0.0, 1.1.0, 1.1.1)
3. Keep your secrets secure and never expose them in your code or commit history
4. Regularly update your dependencies and GitHub Action versions
5. Secure Storage: Keep your KeyStore file (.jks) secure. Never commit it to your repository.
6. Strong Passwords: Use strong, unique passwords for your KeyStore and key.
7. Limited Access: Restrict access to the KeyStore file and its passwords to only those who absolutely need it.
8. Regular Updates: Periodically update your certificates and keys, especially for production use.
9. Backup: Safely backup your KeyStore file. Losing it means losing the ability to sign with that key.
10. Separate Development and Production: Use different certificates and KeyStores for development/testing and production environments.

## Troubleshooting

If you encounter issues:

1. Check the GitHub Actions logs for detailed error messages
2. Ensure all secrets are correctly set up and their values are accurate
3. Verify that your keystore and certificate are valid and not expired
4. Make sure your Gradle build script is correctly configured to use the signing parameters

To verify the contents of your KeyStore:

```bash
keytool -list -v -keystore keystore.jks
```

This will list all certificates in the KeyStore, allowing you to verify the alias, validity period, and other details.

Common issues and solutions:
- "keystore password was incorrect" error: Double-check the password you're using.
- Certificate import fails: Ensure you're using the correct alias and that the certificate matches the key pair in the KeyStore.
- Signing process fails: Verify that the certificate in the KeyStore matches the one you exported and encoded for the GitHub secret.

By following this guide, you'll have a robust CI/CD pipeline that automatically builds your module for pull requests and creates signed releases, streamlining your development and release process for the Example Component Library.