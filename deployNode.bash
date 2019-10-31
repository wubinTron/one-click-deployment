#!/usr/bin/env bash

echo "Start ssh deployment"
noCheck="StrictHostKeyChecking no"

ssh -p $2 $3@$1 -o "${noCheck}" "rm -rf java-tron"
result=`ssh -p $2 $3@$1 "mkdir java-tron"`
if [ -z "$str" ];then
  echo "made the directory"
else
  echo "ssh connect failed"
  echo $result
  exit
fi

scp -P $2  $4 $3@$1:./java-tron/

result=`scp -P $2  ./config.conf $3@$1:./java-tron/`
if [ -z "$str" ];then
  echo "uploaded the jar"
else
  echo "update jar and config failed"
  exit
fi

echo "uploaded the config file"
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

echo  "Finish"