#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main(){
  int pid = fork();
  if(pid==0){
    printf("On est dans le processus fils de %d %d\n",getpid(),getppid());
  }else{
    printf("On est dans le processus père de %d\n",getpid());
  }

  for(;;); // le processus pèreb boucle
  return 0;
}