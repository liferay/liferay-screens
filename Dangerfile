# Warn about develop branch
current_branch = env.request_source.pr_json["base"]["ref"]
warn("Please target PRs to `develop` branch") if current_branch != "develop" && current_branch

# Sometimes it's a README fix, or something like that - which isn't relevant for
declared_trivial = github.pr_title.include? "#trivial"

# Warn no CHANGELOG
fail("No CHANGELOG changes made") if !git.modified_files.include?("CHANGELOG.md") && !declared_trivial

# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# If these are all empty something has gone wrong, better to raise it in a comment
if git.modified_files.empty? && added_files.empty? && deleted_files.empty?
  fail "This PR has no changes at all, this is likely a developer issue."
end