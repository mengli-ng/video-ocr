#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

time=`date -j -f "%Y-%m-%d %T" "$2 00:00:00" +%s | tr -d '\n'`
text=`printf "$1:$time" | openssl enc -base64 -A`

printf $text | openssl dgst -sha256 -binary -sign "$dir/private.pem" -out "$dir/activation_code.tmp"
printf "$text:`openssl enc -base64 -A -in $dir/activation_code.tmp`" > "$dir/activation_code"
rm "$dir/activation_code.tmp"
