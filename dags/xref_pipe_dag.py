from builtins import range
from airflow.operators.bash_operator import BashOperator
from airflow.models import DAG, Variable
from datetime import datetime, timedelta
from croniter import croniter
from airflow.operators.email_operator import EmailOperator
import os

dag_id = 'exercise_v03'
schedule_interval = '*/1 * * * *'
from_date = '2019-01-13'

args = {
    'owner': 'airflow',
    'depends_on_past': True,
    'retries': 10000,
    'retry_delay': timedelta(seconds=60),
    'start_date': datetime.strptime('2019-03-06 00:44:00', "%Y-%m-%d %H:%M:%S"),
    'execution_timeout': timedelta(seconds=300)
}

dag = DAG(
    dag_id=dag_id, default_args=args,
    schedule_interval=schedule_interval)


query_executor = BashOperator(
    task_id='query_executor',
    bash_command='sh -c \'$EXERCISE_HOME/tasks/query_executor.sh {{ FROM_DATE }} \'',
    params = {'FROM_DATE' : from_date},
    dag=dag)


xref_report = BashOperator(
    task_id='xref_report',
    bash_command='sh -c \'$EXERCISE_HOME/tasks/xref_report.sh\'',
    retries=3,
    dag=dag)

xref_report.set_upstream(query_executor)
