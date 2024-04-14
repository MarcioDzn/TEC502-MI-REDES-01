from datetime import datetime


def get_current_time():
    curr_time = datetime.now()

    hour = curr_time.hour
    if (hour < 10):
        hour = f"0{hour}"

    minute = curr_time.minute
    if (minute < 10):
        minute = f"0{minute}"
        
    second = curr_time.second
    if (second < 10):
        second = f"0{second}"


    return f"{hour}:{minute}:{second}"