import { CardContainer, CardInfos, CardStatus, CardTime, CardTitle, CardValue } from "./SensorCardStyled";

export function SensorCard({id, name, time, value, onClick}) {
    return (
        <CardContainer onClick={() => {onClick(id)}}>
            <CardInfos>
                <CardTitle>{name}</CardTitle>
                <CardTime>{time}</CardTime>
            </CardInfos>

            <CardValue>{value}</CardValue>

            <CardStatus>
                <i className="bi bi-wifi"></i>
                Conectado   
            </CardStatus>
        </CardContainer>
    )
}

