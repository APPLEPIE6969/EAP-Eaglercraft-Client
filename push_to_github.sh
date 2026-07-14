#!/bin/bash
# ============================================================================
# Push the Eaglercraft 26.2 port kit to your GitHub repo
# ============================================================================
#
# USAGE:
#   1. Create an empty repo on GitHub: https://github.com/new
#      - DO NOT add README, .gitignore, or license (this kit has them)
#      - Note the repo URL, e.g. https://github.com/YOUR_USERNAME/YOUR_REPO
#
#   2. Run this script with your repo URL:
#      ./push_to_github.sh https://github.com/YOUR_USERNAME/YOUR_REPO.git
#
#   3. When prompted, paste a GitHub Personal Access Token (PAT).
#      Create one at: https://github.com/settings/tokens
#      - Use a "Fine-grained" token scoped to just this repo
#      - Grant only "Contents: Read and write" permission
#      - Set it to expire in 7 days (or less)
#      - The token is used only for this single push, then discarded
#
# WHAT THIS SCRIPT DOES:
#   - Adds your repo as the 'origin' remote
#   - Pushes the local commit (9238011) to your repo's main branch
#   - Does NOT store your token anywhere persistent
# ============================================================================

set -e

if [ -z "$1" ]; then
    echo "Usage: $0 <github-repo-url>"
    echo ""
    echo "Example:"
    echo "  $0 https://github.com/YOUR_USERNAME/YOUR_REPO.git"
    exit 1
fi

REPO_URL="$1"

# Verify the local commit exists
if ! git log --oneline | head -1 | grep -q "Eaglercraft 26.2 port kit"; then
    echo "ERROR: Local commit not found. Did you unzip the port kit correctly?"
    echo "Expected commit message: 'Eaglercraft 26.2 port kit'"
    exit 1
fi

echo "Local commit:"
git log --oneline
echo ""

# Remove any existing origin (in case of re-run)
git remote remove origin 2>/dev/null || true

# Add the remote
echo "Adding remote: $REPO_URL"
git remote add origin "$REPO_URL"

# Push — git will prompt for username/password.
# Username: your GitHub username
# Password: paste your PAT (it will not echo)
echo ""
echo "========================================"
echo "Pushing to GitHub..."
echo "========================================"
echo "When prompted:"
echo "  Username for 'https://github.com': YOUR_GITHUB_USERNAME"
echo "  Password for 'https://...@github.com': PASTE_YOUR_PAT_HERE"
echo ""
echo "(The PAT will not be displayed or stored.)"
echo ""

git push -u origin main

echo ""
echo "========================================"
echo "Push complete!"
echo "========================================"
echo "Your repo: ${REPO_URL%.git}"
echo ""
echo "Next steps:"
echo "  1. Verify the files appear on GitHub"
echo "  2. Delete the PAT you created (https://github.com/settings/tokens)"
echo "  3. To build: clone the repo to a machine with Java 26 + 16 GB RAM,"
echo "     place minecraft-26.2.jar in the root, run ./build.sh"
