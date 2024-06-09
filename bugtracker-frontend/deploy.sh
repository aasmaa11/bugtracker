#!/usr/bin/env sh

set -e

npm run build

cd dist

git init

git add -A

git commit -m "New deployment"

git push -f git@github.com:aasmaa11/personal-bugtracker.git master:gh-pages

cd -