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


    # conjunto de opções (ações) possíveis no dispositivo
    def get_options(self):
        while True:
            print("\n[1] Ligar dispositivo")
            print("[2] Desligar dispositivo")
            print("[3] Alterar valor padrão\n")

            resp = int(input("Escolha uma opcao: "))

            if (resp == 1):
                self.handle_requests("turn_on")

            elif (resp == 2):
                self.handle_requests("turn_off")

            elif (resp == 3):
                new_value = input("Informe o novo valor: ")
                self.set_default_value(new_value)