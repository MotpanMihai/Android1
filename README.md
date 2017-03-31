We had some issues with the repository, so now, just before the deadline we created a new one and we will commit the whole code at once. Because of this we will write bellow what each of us did:
Codrin Costea:
- Grades.java
- GradeActivity.java
- GradeAdapter.java
- HomeActivity.java
- Part of MainActivity.java:
	- private void changeFragment(Fragment f)
	- public void onFragmentInteraction(String token)
	- public class JSONTask extends AsyncTask<String, Void, MyAccount> 
- activity_grades.xml
- activity_grades_list_layout.xml
- activity_main.xml
Mihai Motpan:
- MyAccount.java
- Schedule.java
- SchedulaAdapter.java
- ScheduleActivity.java
- Token Fragment
- account.xml
- activity_schedule.xml
- activity_schedule_list_layout.xml
- fragment.token.xml
- Part of MainActivity.java:
	- public class MainActivity
	- protected void onCreate(Bundle savedInstanceState)
	- public class JSONTaskGrades extends AsyncTask<String, Void, List<Grades>>
	- public class JSONTaskSchedule extends AsyncTask<String, Void, List<Schedule>>