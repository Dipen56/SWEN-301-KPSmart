**** **NOTE: NEVER WORK OF THE MASTER BRANCH** ****

_MOST IMPORTANT COMMANDS USED EVERY TIME_

to pull files from online:  
`git pull` or `git pull origin <branch name>`

to add all files:  
`git add -A`

to commit files:  
`git commit -m "some comment"`   
(if you enter vim the use :x to get out of it)

to push:  
`git push`  
 or  
 `git push origin <branch name>`

to merge branches go to the branch you want to merge onto, and then:  
`git merge <branch name>`

to change branches:  
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

----------------------------------------------------------------------------------------------------------------
to rebase your branch onto master (so you can working on the newest version):  
1. save your current progress (by either commit or stash)
2. checkout to master `git checkout master`, then pull from it `git pull`
3. checkout back to your branch `git checkout <your_branch>`
4. rebase on master `git rebase master`
5. after rebase, the next push you may need to force push `git push -f`. Before doing this, make sure you are in your own branch first!!! And NEVER FORCE PUSH TO MASTER  
