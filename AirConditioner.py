from Device import *

class AirConditioner(Device):

    
    def __init__(self, name, default_value=20):
        super().__init__(name)
        self.default_value = default_value


    def get_data(self):
        formatted_data = f"{self.default_value}°C"

        return formatted_data
    

    def set_default_value(self, value):
        self.default_value = value