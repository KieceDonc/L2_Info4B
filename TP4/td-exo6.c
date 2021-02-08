#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main(){
  int parentPID = getpid();
  for(int x=0;x<5;x++){
    if(getpid()==parentPID){
      int value = fork();
      printf("%d %d\n",getpid(),getppid());
    }
  }
  return 0;
}