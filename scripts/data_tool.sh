#!/bin/bash
# bash generate random alphanumeric string
#

source ./os_match.sh
OS=os_match

random_str(){
  length=$1
  if [[ "$OS" -eq "mac" ]]; then
    cat /dev/urandom |env LC_CTYPE=C tr -dc 'a-zA-Z0-9' | fold -w $length | head -n 1
  else
    cat /dev/urandom |tr -dc 'a-zA-Z0-9' | fold -w $length | head -n 1
  fi
}

random_number(){
  lower=$1
  higher=$2
  if [[ "$OS" -eq "mac" ]]; then
    cat /dev/urandom |env LC_CTYPE=C tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1
  else
    cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1
  fi
}

# bash generate random 32 character alphanumeric string (upper and lowercase) and 
#NEW_UUID=$(`cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1`)
# bash generate random 32 character alphanumeric string (lowercase only)

# Random numbers in a range, more randomly distributed than $RANDOM which is not
# very random in terms of distribution of numbers.

# bash generate random number between 0 and 9
#cat /dev/urandom |  tr -dc '0-9' | fold -w 256 | head -n 1 | head --bytes 1

# bash generate random number between 0 and 99
#NUMBER=$(`cat /dev/urandom | tr -dc '0-9' | fold -w 256 | head -n 1 | sed -e 's/^0*//' | head --bytes 2`)
#if [ "$NUMBER" == "" ]; then
#  NUMBER=0
#fi

# bash generate random number between 0 and 999
#NUMBER=$(cat /dev/urandom | tr -dc '0-9' | fold -w 256 | head -n 1 | sed -e 's/^0*//' | head --bytes 3)
#if [ "$NUMBER" == "" ]; then
#  NUMBER=0
#fi
