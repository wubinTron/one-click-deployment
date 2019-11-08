#!/usr/bin/env bash

echo "Start ssh deployment"
finish="deploy finish"
noCheck="StrictHostKeyChecking no"

ssh -p $2 $3@$1 -o "${noCheck}" "rm -rf java-tron"
result=`ssh -p $2 $3@$1 "mkdir java-tron" 2>&1`
if [ -z $result ];then
  echo "made the directory"
else
  echo "ssh connect failed, ${finish}"
  echo $result
  exit
fi

echo "uploading java-tron jar"
scp -P $2  $4 $3@$1:./java-tron/
result=`scp -P $2 $5 $3@$1:./java-tron/config.conf 2>&1`
if [ -z $result ];then
  echo "already uploading java-tron jar and config"
else
  echo "update java-tron jar and config failed, ${finish}"
  exit
fi

if [ -z $6 ]; then
   echo "deploy FullNode"
   scp -P $2 ./startFullNode.sh $3@$1:./java-tron/
   ssh -p $2 $3@$1 "cd java-tron&& nohup bash startFullNode.sh"
else
   echo "deploy WitnessNode"
   cmd="cd java-tron&& nohup bash startWitnessNode.sh ${6}"
   scp -P $2 ./startWitnessNode.sh $3@$1:./java-tron/
   ssh -p $2 $3@$1 "cd java-tron&& nohup bash startWitnessNode.sh ${6}"
fi

rm -rf $5
echo  "${finish}"