/*
 * corpus.c

 *
 *  Created on: 2015. 12. 14.
 *      Author: ismean21
 */


#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <malloc.h>
#include <ctype.h>


#define MAX_WORD_LEN	100
#define MAX_LINE_LEN	500
#define REMOVED_XML_TAG_FILE_PATH	"C:/Users/Administrator/Documents/Visual Studio 2013/Projects/Corpus_C/removedXmlTag.txt"
#define TEST_XML_FILE_PATH			"C:/Users/Administrator/Documents/Visual Studio 2013/Projects/Corpus_C/test.xml"
#define CORPUS_TXT_FILE_PATH		"C:/Users/Administrator/Documents/Visual Studio 2013/Projects/Corpus_C/corpus.txt"

#define HASH_TABLE_SIZE		18954		//26*27*27
#define MAX_WORD_NUM		300000		//¾î¸²°ª


typedef struct WordArray {
	char word[MAX_WORD_LEN];
	void *next;
	int count;
} typeWArray;

typeWArray *global_HASH_ARRAY[HASH_TABLE_SIZE] = { NULL };
int global_WORD_MAP_COUNT = 0;

typeWArray global_WORD_ARRAY[MAX_WORD_NUM];


int getHashCode(char *word){
	int len = strlen(word);
	int hashCode = 0;

	if ('a' <= word[0] && word[0] <= 'z'){
		hashCode = (int)(word[0] - 'a') * 27 * 27;
	}
	else{
		hashCode = 0;
	}

	if (len >= 2){

		if ('a' <= word[1] && word[1] <= 'z'){
			hashCode += (int)(word[1] - 'a' + 1) * 27;
		}
	}

	if (len >= 3){
		if ('a' <= word[2] && word[2] <= 'z'){
			hashCode += (int)(word[2] - 'a' + 1);
		}
	}
	return hashCode;
}

void wordCounting(char *word){
	int hashCode = getHashCode(word);
	typeWArray *ptr1 = NULL;
	typeWArray *ptr2 = NULL;
	int cmpValue = 0;
	typeWArray *wordIndex;

	if (hashCode >= HASH_TABLE_SIZE){
		printf("ERROR : hashTable Size over\n");
		return;
	}

	if (global_WORD_MAP_COUNT >= MAX_WORD_NUM){
		printf("ERROR : wordMap Size over\n");
		return;
	}

	wordIndex = global_HASH_ARRAY[hashCode];

	// hashcode°¡ Ã³À½ µé¾î¿ÔÀ» ‹š,
	if (wordIndex == NULL){
		global_HASH_ARRAY[hashCode] = &(global_WORD_ARRAY[global_WORD_MAP_COUNT]);
		strcpy(global_WORD_ARRAY[global_WORD_MAP_COUNT].word, word);
		global_WORD_ARRAY[global_WORD_MAP_COUNT].next = NULL;
		global_WORD_ARRAY[global_WORD_MAP_COUNT].count = 1;
		global_WORD_MAP_COUNT++;
	}	//ÀÌ¹Ì HashCode°¡ ÀÖ´Â °æ¿ì
	else{
		ptr1 = wordIndex;
		do{
			cmpValue = strcmp(word, ptr1->word);
			if (cmpValue == 0){
				ptr1->count += 1;
				break;
			}
			else if (cmpValue < 0){
				global_HASH_ARRAY[hashCode] = &(global_WORD_ARRAY[global_WORD_MAP_COUNT]);
				strcpy(global_WORD_ARRAY[global_WORD_MAP_COUNT].word, word);
				global_WORD_ARRAY[global_WORD_MAP_COUNT].next = ptr1;
				global_WORD_ARRAY[global_WORD_MAP_COUNT].count = 1;
				global_WORD_MAP_COUNT++;
				break;
			}
			else{
				ptr2 = ptr1;
				ptr1 = ptr1->next;
			}

		} while (ptr1 != NULL);
		if (ptr1 == NULL){
			strcpy(global_WORD_ARRAY[global_WORD_MAP_COUNT].word, word);
			global_WORD_ARRAY[global_WORD_MAP_COUNT].next = NULL;
			global_WORD_ARRAY[global_WORD_MAP_COUNT].count = 1;
			ptr2->next = &global_WORD_ARRAY[global_WORD_MAP_COUNT];
			global_WORD_MAP_COUNT++;

		}
	}
}


void wordArrayPrint(){
	int i = 0;
	int wcount = 0;
	typeWArray *ptr = NULL;
	FILE *pFileW = fopen(CORPUS_TXT_FILE_PATH, "w");

	if (pFileW != NULL){

		for (i = 0; i < HASH_TABLE_SIZE; i++){
			if (global_HASH_ARRAY[i] != NULL){
				//ptr = &(global_WORD_ARRAY[global_HASH_ARRAY[i]]);
				ptr = global_HASH_ARRAY[i];
				while (ptr != NULL){
					fprintf(pFileW, "%s : %d \n", ptr->word, ptr->count);
					//printf("%d  %s : %d \n", i, ptr->word, ptr->count);
					wcount += ptr->count;
					ptr = ptr->next;
				}
			}
		}
		fclose(pFileW);
		printf("wcount : %d \n", wcount);
	}
	else{

	}
}

//´Ü¾î ÀüÃ³¸® ÇÔ¼ö
void preProcessing(char *word){
	int len = strlen(word);
	int i = 0;

	//¼Ò¹®ÀÚ·Î º¯È¯
	//printf("1 \n");
	for (i = 0; i < len; i++){

		if ('A' <= word[i] && 'Z' >= word[i]){
			word[i] = tolower(word[i]);
		}

	}
	//printf("¼Ò¹®ÀÚ º¯È¯ ¿Ï·á + %s \n", word);

	//Æ¯¼öºÎÈ£ Á¦°Å
	int startPoint = 0;
	int endPoint = len - 1;
	int j = 0;
	char str[MAX_WORD_LEN] = { '\0' };
	//printf("startPoint °è»ê ½ÃÀÛ\n");
	for (i = 0; i < len; i++){

		if ('a' <= word[i] && 'z' >= word[i]){
			startPoint = i;
			break;
		}

	}
	if (i < len) {

		//printf("endPoint °è»ê ½ÃÀÛ\n");
		for (i = len - 1; i >= startPoint; i--){

			if ('a' <= word[i] && word[i] <= 'z'){
				endPoint = i;
				break;
			}

		}
		//printf("startPoint : %d, endPoint : %d\n", startPoint, endPoint);
		//printf("str Concat ½ÃÀÛ\n");
		for (i = startPoint; i <= endPoint; i++){
			str[j++] = word[i];
		}
		str[j] = '\0';

		//printf("end\n");
		// Á¾·á
		strcpy(word, str);
		//printf("strcpy ¿Ï·á\n");
	}
	else{		//¼ø¼öÇÏ°Ô Æ¯¼öºÎÈ£·Î ÀÌ·ç¾îÁø ´Ü¾îÀÏ ‹š,
		word[0] = '\0';
	}
}

int isBeVerb(char *word){
	if (strcmp(word, "be") == 0 || strcmp(word, "is") == 0 || strcmp(word, "are") == 0 || strcmp(word, "am") == 0 ||
		strcmp(word, "were") == 0 || strcmp(word, "was") == 0 || strcmp(word, "been") == 0){
		return 1;
	}
	else{
		return 0;
	}
}
int isArticle(char *word){
	if (strcmp(word, "a") == 0 || strcmp(word, "an") == 0 || strcmp(word, "the") == 0){
		return 1;
	}
	else{
		return 0;
	}

}



void main(int argc, char **argv)
{
	FILE *pFileR = fopen(TEST_XML_FILE_PATH, "r");
	FILE *pFileW = fopen(REMOVED_XML_TAG_FILE_PATH, "w");

	//	printf("start inpt file read\n");
	if (pFileR != NULL){
		char str[MAX_LINE_LEN];
		char *pStr;

		while (!feof(pFileR)){
			pStr = fgets(str, sizeof(str), pFileR);
			int i = 0;
			int tagFlag = 0;
			char line[MAX_LINE_LEN] = { 0 };
			int linePoint = 0;

			while (str[i] != '\0'){
				if (str[i] == '<'){
					tagFlag = 1;
				}
				if (tagFlag == 0){
					line[linePoint++] = str[i];
				}
				if (str[i] == '>'){
					tagFlag = 0;
				}
				i++;
			}
			fprintf(pFileW, "%s", line);
			//printf("%s", line);
		}
		fclose(pFileR);
		fclose(pFileW);
	}
	else{
		printf("File doesn't exist.\n");
	}
	//printf("end inpt file read\n");

	//printf("Start ÃÊ±âÈ­\n");
	//Àü¿ª º¯¼ö ÃÊ±âÈ­
	global_WORD_MAP_COUNT = 0;
	int i = 0;
	for (i = 0; i < HASH_TABLE_SIZE; i++){
		global_HASH_ARRAY[i] = NULL;
	}
	for (i = 0; i < MAX_WORD_NUM; i++){
		global_WORD_ARRAY[i].word[0] = '\0';
		global_WORD_ARRAY[i].next = -1;
		global_WORD_ARRAY[i].count = 0;
	}
	//printf("end ÃÊ±âÈ­\n");

	//¿©±â±îÁö xmlÆÄÀÏ ReadÇÏ°í, xmlÅ×±× Á¦°ÅÇØ¼­ txtÆÄÀÏ Write.
	printf("Å×±× Á¦°Å ÆÄÀÏ »ý¼º ½ÃÀÛ\n");
	if ((pFileR = fopen(REMOVED_XML_TAG_FILE_PATH, "r")) != NULL){

		char word[MAX_WORD_LEN] = { '\0' };
		while (fscanf(pFileR, "%s", word) != EOF){
			//printf("%s", word); getchar();
			preProcessing(word);
			//printf(" -> %s\n", word);
			if (word[0] == '\0' || isBeVerb(word) || isArticle(word)){
				continue;
			}
			//fflush(stdout);
			//printf(" befpre :  %s\n", word);
			wordCounting(word);
			//printf(" after :  %s\n", word);

		}
		printf("Å×±× Á¦°Å ÆÄÀÏ »ý¼º ³¡\n");
		fclose(pFileR);
		wordArrayPrint();
		printf(" total words : %d \n", global_WORD_MAP_COUNT);

	}
	else{
		printf("File ( %s ) doesn't exist \n", REMOVED_XML_TAG_FILE_PATH);
	}
	printf("end removed file read\n");

}
