---
title: Pull Request Guide
description: How to submit and manage pull requests for the Example Component Library
---

# Pull Request Guide

This guide explains our pull request process and best practices.

## Before Creating a PR

1. Ensure your changes follow our [Code Style Guide](./code-style)
2. Update relevant documentation
3. Add or update tests
4. Test your changes locally

## Creating a Branch

```bash
# For new features
git checkout -b feature/your-feature-name

# For bug fixes
git checkout -b fix/your-bug-fix

# For documentation
git checkout -b docs/your-doc-change
```

:::tip Branch Naming
Use descriptive names that reflect the change:

- `feature/add-datepicker`
- `fix/button-alignment`
- `docs/update-setup-guide`
  :::

## Making Changes

1. Make focused, specific changes
2. Commit regularly with clear messages
3. Keep commits atomic (one logical change per commit)
4. Rebase on main if needed

## Preparing Your PR

### 1. Update Your Branch

```bash
git fetch upstream
git rebase upstream/main
```

### 2. Run Tests

```bash
# Run Java tests
./gradlew test

# Run web tests
cd web && npm test
```

### 3. Build the Project

```bash
./gradlew clean build
```

## Submitting Your PR

1. Push your changes:

   ```bash
   git push origin feature/your-feature-name
   ```

2. Create a PR through GitHub

3. Fill out the PR template:

   ```markdown
   ## Description

   Brief description of changes

   ## Related Issues

   Fixes #123

   ## Testing Performed

   - [ ] Unit tests added/updated
   - [ ] Manual testing completed
   - [ ] Documentation updated

   ## Screenshots

   (if applicable)

   ## Checklist

   - [ ] Code follows style guide
   - [ ] Tests passing
   - [ ] Documentation updated
   - [ ] Commits are clean
   ```

## PR Review Process

### What to Expect

1. Automated checks will run
2. Maintainers will review your code
3. You may receive feedback requesting changes
4. Once approved, your PR will be merged

### Handling Feedback

1. Address all comments
2. Make requested changes
3. Push new commits
4. Request re-review when ready

:::tip Review Response

- Respond to all comments
- Ask for clarification if needed
- Be open to feedback
- Keep discussions professional
  :::

## After Merge

1. Delete your branch
2. Update your local repository
3. Celebrate your contribution! 🎉

## Common PR Issues

### Size

- Keep PRs focused and manageable
- Split large changes into smaller PRs
- Link related PRs in descriptions

### Documentation

- Update relevant docs
- Include code comments
- Add examples if needed

### Tests

- Add unit tests
- Update existing tests
- Verify all tests pass

## Tips for Success

1. **Communication**

   - Describe changes clearly
   - Respond promptly to feedback
   - Ask questions if unsure

2. **Quality**

   - Follow code style
   - Include tests
   - Update documentation

3. **Process**
   - Keep commits clean
   - Rebase when needed
   - Be patient during review

## Need Help?

- Check our [Code Style Guide](code-style)
- Ask in GitHub discussions

Remember, every contribution helps make the project better! Thank you for your efforts! 🙏
