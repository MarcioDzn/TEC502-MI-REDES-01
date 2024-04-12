from datetime import datetime


def get_current_time():
    curr_time = datetime.now()

    return f"{curr_time.hour}:{curr_time.minute}:{curr_time.second}"