**** **NOTE: NEVER WORK OF THE MASTER BRANCH** ****

_MOST IMPORTANT COMMANDS USED EVERTIME_

to pull files from online:
`git pull` or `git pull origin <branch name>`

to add all files:
`git add -A`

to commit files:
`git commit -m "some comment"` (if you enter vim the use :x to get out of it)

to push:
`git push` or `git push origin <branch name>`

to merge braches go to the branch you want to merge onto, and then:
`git merge <branch name>`

to change branchs:
`git checkout <branch name>`


--------------------------------------------------------------------------------------------------------------
for force pull / hard reset local files:
`git fetch --all`

Then, you have two options:
`git reset --hard origin/<branch name>`

OR If you are on some other branch:
`git reset --hard origin/<branch_name>`

http://stackoverflow.com/questions/1125968/how-do-i-force-git-pull-to-overwrite-local-files

----------------------------------------------------------------------------------------------------------------
creating a branch:
`git checkout -b <branchname>`

putting the branch online:
`git push --set-upstream origin <branch name>`

----------------------------------------------------------------------------------------------------------------
linking up to an online repository:
`git clone <url>`
----------------------------------------------------------------------------------------------------------------
to track a branch so that you don't have to use origin in front of it:
`git branch --set-upstream-to=origin/<branch name>`
