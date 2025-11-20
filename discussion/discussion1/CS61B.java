/** Haha get it...because CS61B is a class...at a public university...I'll see myself out */
public class CS61B {
// Variables (part a)
    public static String university="UC Berkeley";
    public String semester;
    public CS61BStudent[] students;

// Constructor (part b)
    public CS61B(int capacity,CS61BStudents[] Signups,String semester){
        this.semester=semester;
        this.students=new CS61BStudent[capacity];
        for(int i=0;i<capacity;i++){
            this.students[i]=Signups[i];
        }
    }

// Methods (part c)
/** Makes every CS61BStudent enrolled in this semester of the course watch lecture. Returns the
 number of students who actually watched lecture. */
    public int makeStudentsWatchLecture{
        int nums=0;
        for(int i=0;i<this.students.length;i++){
            if(this.students[i].watchLecture()){
                nums++;
            }
        }
        return nums;
    }
/** Takes in a new university name newUniversity and changes the university
 for all semesters of CS61B to newUniversity. */
    public static void changeUniversity(String newUniversity){
        university=newUniversity;
    }
// Expansion (part d)
/** Expands the course to the given capacity. */

}