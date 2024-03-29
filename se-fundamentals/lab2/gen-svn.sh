#!/bin/bash

# Most of the function assume that PWD is REPO_DIR.

COMMITS_DIR="./commits"
COMMITS_DIR_ABS_PATH="$(pwd)/$COMMITS_DIR"
REPO_DIR="./lab2-svn"
REPO_DIR_ABS_PATH="$(pwd)/$REPO_DIR"

current_user="red"
REPO="lab2-svn"
REPO_URL="svn://0.0.0.0:3690/$REPO"

say() {
    echo "$@" >&2;
}

prepare_repo() {
    say "Hope you recreated $REPO repo!"

    if [[ -d "$REPO_DIR" ]]
    then
        say "Directory '$REPO_DIR' already exists."
        exit 1
    fi

    svn checkout "$REPO_URL"

    cd $REPO

    svn mkdir trunk branches
    svn commit -m "basic structure" --username $current_user --password $current_user
    svn update

    say "Repo created"
}

remove_deleted() {
    # svn status: list changes
    # grep: only deleted files (!)
    # awk: leaves only filenames
    # xargs: every line = argument, pass to svn rm
    svn status | grep '^!' | awk '{print $NF}' | xargs -I% svn rm --force %
}

add_all() {
    svn add --force *
}

create_commit() {
    local commit_n=$1
    local archive_path="$COMMITS_DIR_ABS_PATH/commit$commit_n.zip"

    find . -type f -not -path '*.svn*' -exec rm {} \;
    unzip -o -d "." "$archive_path"

    add_all
    remove_deleted

    svn update
    svn commit -m "commit message (#$commit_n)" --username $current_user --password $current_user
    svn update

    local branch="$(svn info --show-item relative-url)"

    say "Commit #$commit_n on $branch (by $current_user)"
}

switch_user() {
    local user=$1

    if [[ "$user" == "2" ]]
    then
        current_user="blue"
    else
        current_user="red"
    fi

    say "'Switched' to user $current_user"
}

switch_branch() {
    local branch=$1
    svn update
    svn switch ^/branches/$branch --ignore-ancestry
    svn update

    say "Switched to branch $branch"
}

create_branch_from_trunk() {
    local new_branch=$1

    svn update
    svn copy ^/trunk ^/branches/$new_branch -m "create $new_branch" --username $current_user --password $current_user
    svn update

    say "Created branch $new_branch"
}

create_branch_from_branch() {
    local branch=$1
    local new_branch=$2

    svn update
    svn copy ^/branches/$branch ^/branches/$new_branch -m "create $new_branch" --username $current_user --password $current_user
    svn update

    say "Created branch $new_branch"
}

print_delimiter() {
    sleep 1
    say "---------"
}

merge_branch() {
    local other=$1
    local commit_n=$2

    local current="$(git branch --show-current)"

    svn update
    svn merge "^/branches/$other" --accept=postpone

    local unmerged=$(svn status | grep -P '^(?=.{0,6}C)')

    if [[ ! -z "$unmerged" ]]
    then
        say "Merge conflict occured. Please resolve all conflicts in other terminal session then hit Enter here."
        say "Conflicts occured in:"
        say "$unmerged"
        read -n 1 -s -r

        remove_deleted
        echo $unmerged | awk '{print $NF}' | xargs -I% svn resolved %
        add_all
    fi

    svn commit -m "merge message (#$commit_n)" --username $current_user --password $current_user
    svn update

    say "Merged $other into $current"
}

main() {
    prepare_repo

    print_delimiter
    # r0
    switch_user 1
    create_branch_from_trunk "branch1"
    switch_branch "branch1"
    create_commit 0

    print_delimiter
    # r1
    switch_user 2
    create_branch_from_branch "branch1" "branch2"
    switch_branch "branch2"
    create_commit 1

    print_delimiter
    # r2
    switch_user 1
    switch_branch "branch1"
    create_commit 2

    print_delimiter
    # r3
    switch_user 2
    create_branch_from_branch "branch1" "branch3"
    switch_branch "branch3"
    create_commit 3

    print_delimiter
    # r4
    create_commit 4

    print_delimiter
    # r5
    switch_branch "branch2"
    create_commit 5

    print_delimiter
    # r6
    switch_branch "branch3"
    create_commit 6

    print_delimiter
    # r7
    switch_user 1
    switch_branch "branch1"
    create_commit 7

    print_delimiter
    # r8
    switch_user 2
    switch_branch "branch3"
    create_commit 8

    print_delimiter
    # r9
    switch_branch "branch2"
    create_commit 9

    print_delimiter
    # r10
    create_commit 10

    print_delimiter
    # r11
    switch_user 1
    switch_branch "branch1"
    create_commit 11

    print_delimiter
    # r12
    switch_user 2
    switch_branch "branch2"
    create_commit 12

    print_delimiter
    # r13: merge branch2 into branch3
    switch_branch "branch3"
    merge_branch "branch2" 13

    print_delimiter
    # r14: merge branch3 into branch1
    switch_user 1
    switch_branch "branch1"
    merge_branch "branch3" 14
}

main >/dev/null