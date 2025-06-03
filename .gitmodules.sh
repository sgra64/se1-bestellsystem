# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
# Output of the the git_submodules_add() function are commands generated from
# the .gitmodules file:
# //
# git submodule add -b env -f    -- https://github.com/sgra64/se1-gitmodules.git .env
# git submodule add -b vscode -f -- https://github.com/sgra64/se1-gitmodules.git .vscode
# git submodule add -b libs -f   -- https://github.com/sgra64/se1-gitmodules.git libs
# 
# Commands can be executed by using the --exec flag.
# Usage:
# - source .gitmodules.sh && git_submodules_add [--exec]
# 
# Example .gitmodules file:
# - [submodule ".env"]
#         path = .env
#         url = https://github.com/sgra64/se1-gitmodules.git
#         branch = env
# - [submodule ".vscode"]
#         path = .vscode
#         url = https://github.com/sgra64/se1-gitmodules.git
#         branch = vscode
# - [submodule "libs"]
#         path = libs
#         url = https://github.com/sgra64/se1-gitmodules.git
#         branch = libs
# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

function git_submodules_add() {
    rm -f .gitmodules-added
    # 
    # Content of the .gitmodules file is flattened to kv-lines:
    # - path=.env url=https://... branch=env
    # - path=.vscode url=https://... branch=vscode ...
    # Lines are read into the kv[] associative array:
    # - declare -A kv=([path]=.env [url]=https://... [branch]=env)
    git config -f .gitmodules -l | \
        sed -e 's/^submodule[\.]*[a-zA-Z0-9]*\.//' -e 's/^path/%path/' -e '$a%' | \
        tr '\n%' ' \n' | sed -e '/^$/d' | \
        \
        while read line; do
            declare -A kv="("$(sed -e 's/path/[path]/' -e 's/url/[url]/' -e 's/branch/[branch]/' <<< $line)")"
            # 
            if [ "$1" = "--exec" ]; then
                if [ -d ".git/modules/${kv[path]}" ]; then
                    echo "Module \"${kv[path]}\" is already present in git under: .git/modules/${kv[path]}"
                elif [ -e "${kv[path]}" ]; then
                    echo "Module \"${kv[path]}\" is already present in project directory: ./${kv[path]}"
                else
                    # execute command
                    git submodule add -b "${kv[branch]}" -f -- "${kv[url]}" "${kv[path]}"
                    echo "Module \"${kv[path]}\" added and staged."
                    echo "${kv[path]} " >> .gitmodules-added
                fi
            else
                echo "git submodule add -b ${kv[branch]} -f -- ${kv[url]} ${kv[path]}"
            fi
        done;
    # 
    if [ -f .gitmodules-added ]; then
        local gitmodules_added=$( < .gitmodules-added)
        rm -f .gitmodules-added
        # 
        if [[ ${#gitmodules_added[@]} > 0 ]]; then
            echo "Committing added git modules: ${gitmodules_added[@]}"
            git commit -m "git module(s) added: ${gitmodules_added[@]}"
        fi
    fi
}

# function git_submodule_remove() {
#     # remove module from git commit
#     # remove paths .git/modules/module or .git/modules
# }
