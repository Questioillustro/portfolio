#define MAXPATIENTS 5
#define MAXREADINGS 10
#define MAXTYPES 5
#define MAXTIME 8
#define HR "--------------------------------------------------"
#define NONE "<none>"

typedef struct Element {
    char timestamp[MAXTIME+1] ;
    int value ;
} Element ;

typedef struct CircularBuffer {
    int start ;
    int end ;
    Element reading[MAXREADINGS] ;
} CircularBuffer ;

typedef struct Chart {
    int id ;
    CircularBuffer buffer[MAXTYPES] ;
} Chart ;

struct Element ;
struct Chart ;
struct CircularBuffer ;

char parse_health_line(struct Element *line, int *id, int *cmd) ;
char* get_stat_string(int cmd) ;
void print_health_data(struct Chart chart) ;
void initialize_chart(struct Chart *chart) ;

int execute_lvl_1() ;
int execute_lvl_2() ;
int execute_lvl_3() ;
int execute_lvl_4() ;
int execute_lvl_5() ;
