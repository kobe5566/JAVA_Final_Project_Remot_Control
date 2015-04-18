#include<iostream>
#include<cstdlib>
#include<cstdio>
#include<ctime>
#define SIZE 9
using namespace std;




//�ťծ檺��T: 1.��y��(i,j) 2.�ư�num[1~9]���i�઺�Ʀr
typedef struct block
{
    int i;
    int j;
    int num[9];
} Block;

class Sudoku
{
public:
    void  ReadIn();
    int check_repeat_num(int i,int j);
    void save_block(int i,int j);
    void  Solve();
    void SolveIt();
    Sudoku();

    void print(int m[SIZE][SIZE]);
    void GiveQuestion();


private:
    int  map[SIZE][SIZE];
    Block block[144];//�ťծ�
    int block_num;//�ťծ�p�ƾ�
    int pointer;//�񵪮׫���
    int sol;//���׼ƶq
    int ans[SIZE][SIZE];

};
