from builtins import range
from airflow.operators.bash_operator import BashOperator
from airflow.models import DAG, Variable
from datetime import datetime, timedelta
from croniter import croniter
import os

dag_id = 'exercise_v03'
schedule_interval = '*/1 * * * *'

args = {
    'owner': 'airflow',
    'depends_on_past': True,
    'retries': 10000,
    'retry_delay': timedelta(seconds=60),
    'start_date': datetime.strptime('2019-02-14 11:30:00', "%Y-%m-%d %H:%M:%S"),
    'execution_timeout': timedelta(seconds=7200),
}

dag = DAG(
    dag_id=dag_id, default_args=args,
    schedule_interval=schedule_interval)


hello_world = BashOperator(
    task_id='hello_world',
    bash_command='sh -c \'$EXERCISE_HOME/tasks/hello_world.sh \"{{ execution_date }}\" \'',
    dag=dag)

if __name__ == "__main__":
    dag.cli()
