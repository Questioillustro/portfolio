/*
<package>
	C Project 1 - swen250
<.package>
<description>
    Executes the level 2 requirements of the project
<.description>
<keywords><.keywords>
*/

int execute_lvl_2() {
    struct Element line ;
    struct Chart chart[MAXPATIENTS] ;

    int id, i;
    int cmd = 0 ;
    char ch ;

    // Initializes the chart buffers to start=0, end=0
    for(i = 0; i < MAXPATIENTS; i++) {
        initialize_chart(&chart[i]) ;
    }

    do {
        ch = parse_health_line(&line, &id, &cmd) ;

        if(ch == EOF) {
            break ;
        }

        // Print chart when cmd = 6, otherwise, aggregate data into the buffer
        if(cmd == 6) {
            print_health_data(chart[id-1]) ;
        } else {
            chart[id-1].id = id ;
            int e = chart[id-1].buffer[cmd-1].end ;
            int s = chart[id-1].buffer[cmd-1].start ;
            chart[id-1].buffer[cmd-1].reading[e] = line ;

            e = (e+1)%MAXREADINGS ;
            if(e <= s) {
                s = (s+1)%MAXREADINGS ;
            }

            chart[id-1].buffer[cmd-1].end = e ;
            chart[id-1].buffer[cmd-1].start = s ;
        }
    } while (ch != EOF) ;
}
