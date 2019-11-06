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

scp -P $2  $4 $3@$1:./java-tron/

echo "uploading java-tron jar"
result=`scp -P $2  ./config.conf $3@$1:./java-tron/ 2>&1`
if [ -z $result ];then
  echo "already uploading java-tron jar and config"
else
  echo "update java-tron jar and config failed, ${finish}"
  exit
fi

if [ -z $5 ]; then
   echo "deploy FullNode"
   scp -P $2 ./start.sh $3@$1:./java-tron/
   ssh -p $2 $3@$1 "cd java-tron&& nohup bash startFullNode.sh"
else
   echo "deploy WitnessNode"
   cmd="cd java-tron&& nohup bash startWitnessNode.sh ${5}"
   scp -P $2 ./startWitnessNode.sh $3@$1:./java-tron/
   ssh -p $2 $3@$1 "cd java-tron&& nohup bash startWitnessNode.sh ${5}"
fi
echo "Start Running the Node"

echo  "${finish}"