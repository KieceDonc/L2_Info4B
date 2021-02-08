#include <stdio.h>
#include <stdlib.h>
#include <sys/signal.h>

// ps -efly affiche tout les processus

int main(){
  int PID;
  if(fork()==0){
    printf("Je suis le fils de pid %d\n",getpid());
    exit(3);
  }
  printf("Je suis le processus père de pid %d\n",getpid());
  for(;;); // proc pere boucle
}