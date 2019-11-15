#!/usr/bin/env bash
APP="java-tron-1.0.0"

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
result=`scp -P $2  $4 $3@$1:./java-tron/  2>&1`
if [ -z $result ];then
 echo "already uploading java-tron jar"
else
  echo "update java-tron jar failed, ${finish}"
  exit
fi

echo "uploading config.conf"
result=`scp -P $2 $5 $3@$1:./java-tron/config.conf 2>&1`
if [ -z $result ];then
  echo "already uploading config"
else
  echo "update config failed, ${finish}"
  exit
fi

result=`ssh -p $2 $3@$1 "cd java-tron&&unzip -o ./${APP}.zip > /dev/null"`
if [ "$?" != "0" ]; then
   echo "unzip failed, unzip cmd is not installed or java-tron zip upload failed, ${finish}"
   echo $result
   exit
fi

scp -P $2 ./.startNode.sh $3@$1:./java-tron/start.sh

if [ $6 != "null" ]; then
  echo "uploading plugin"
  result=`scp -P $2 $6 $3@$1:./java-tron/$APP/lib/ 2>&1`

  if [ -z $result ];then
    echo "already upload plugin"
  else
    echo "update plugin failed"
    exit
  fi
fi

if [ -z $7 ]; then
   echo "deploy FullNode"
   ssh -p $2 $3@$1 "cd java-tron&& nohup bash start.sh"
else
   echo "deploy WitnessNode"
   ssh -p $2 $3@$1 "cd java-tron&& nohup bash start.sh ${7}"
fi

rm -rf $5
echo  "${finish}"