import { CardContainer, CardInfos, CardStatus, CardTime, CardTitle, CardValue } from "./SensorCardStyled";

export function SensorCard({id, name, time, value, status, selected, onClick}) {
    return (
        <CardContainer onClick={() => {onClick(id)}} selected={selected}>
            <CardInfos>
                <CardTitle>{name}</CardTitle>
                <CardTime>{time}</CardTime>
            </CardInfos>

            <CardValue>{value}</CardValue>

            <CardStatus>
                {
                    status !== "disconnected" ? 
                    <div>
                        <i className="bi bi-wifi"></i>
                        Conectado  
                    </div> : 
                    <div>
                        <i className="bi bi-wifi-off"></i>
                        Desconectado  
                    </div> 

                }
                <span>{status !== "disconnected" ? status : ""}</span>
            </CardStatus>
        </CardContainer>
    )
}

