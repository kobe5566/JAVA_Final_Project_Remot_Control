/*
 *Problem4 ����D  ( Deadline 4/6 )
 *�o�ӹ���D�A�O�@�Ӭy�{�ܶê� code
 *�L���y�{�O�G�п�J�@�Ӿ�� �A�{���|�P�O�o�Ӿ�ƩM 10 ���j�p�C�бN if else block �����{���X�A���u�Τ@��function��@�C������n�X�o�˪��D�ءA�O�]���G
 *�u�����x�ϥΫ��Ъ��H�A�~��N�A�� function (main)�g����²�㦳�Ҳդƪ������A�åB�ǥѳo��code�N���D�A�]�p���覡�ܭ��n�I
 */

#include<stdio.h>

#define STATE_INIT 	  0
#define STATE_PROCESSING  1
#define STATE_END	  2


void state_init(int *state,int *number)
{

    if ( *state == STATE_INIT )
    {

        printf("Please input a integer number, -1 will kill process\n");
        scanf("%d", number);
        *state = STATE_PROCESSING;

    }

}


void state_processing(int *state,int *number)
{


    if ( *number == -1 )
    {
        *state = STATE_END;

        return;
    }

    printf("%s\n", (*number > 10) ? "The number is bigger than 10" :
           "The number is not bigger than 10" );
    printf("-------------------------------------------------\n");
    *state = STATE_INIT;



}


int main()
{

    int state = STATE_INIT;
    int number = 0;

    while ( 1 )
    {

        if ( state == STATE_INIT )
        {
            /*****  �N���Ѱ϶���ӧ令�@�� function ********/
            state_init(&state,&number);
            /***********************************************/
        }
        else if ( state == STATE_PROCESSING )
        {

            /************************************************/
            state_processing(&state,&number);

            /************************************************/
        }
        else if ( state == STATE_END )
        {
            break;
        }
    }



}
