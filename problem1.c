#include<stdio.h>



void chage_num(int *num,const int source){

	
	*num = source;


}





int main(){ 
	
	int number = 10;
	/* ���˼g�@�� function �N number �令�A�Q�n�諸�ȡH */


	//change_num(&number,8);  







	/* �ХΦۤv���Q�k�����U���o�q code ���N�� */
	









	char *str = "hello world";  /*char  (*str) �]���O�ŧi,�ҥH*�ݬ�����,str�������ܼ�,���V��char
				      �]���r�ꬰ�s�򪺦r����Ƶ��c,�ҥH�u�n�ŧi�X�r�ꪺ���Ĥ@�Ӧ�},�N�i�H�s�r��
				      �ĪG�P�}�C���ŧi��zchar str[] = "hello world";*/


	
	while ( *str ) { 		
		printf("%c\n", *str++);	  
	}				/*���q�ԭz�\�ର�L�X�r��,�]���@�ӧ��㪺�r��|�b�̫�K�['\0',��ASCII�Ȭ�0
					  *str����*���O�ŧi�ҥH�O���ȹB��l,��L�Xstr*��,str++�N�|����U�@��F
					str+0 str+1  str+2   �]��char�j�p��1byte, str++��|�N���Ц�}+1byte
					2000  2001   2002  			
						

					*(str+0) *(str+1) *(str+2)
					str[0]    str[1]   str[2]
							*/
	
	
	/* �U���o�q code �A str2 �O�@�ӫ��V char ������
	 * �L�u�V�F�r�� "hello world" ���Ĥ@�Ӧ�}
	 * �b�o�� "hello world" �|�Q���t�b .text �q��A
	 * ��@�O�@�� const ���O����Ū�r��A����i�����ק�
	 * ���O pointer to char : str2 �o�i�H�@������
	 */

	char *str2 = "hello world";
	int i = 0;
	while ( i < 100 ){ 
		printf("%c\n", *str2++);
	}
	return 0;

}











