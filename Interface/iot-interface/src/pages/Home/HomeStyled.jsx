import styled from "styled-components"

export const HomeContainer = styled.section`
    padding: 25px;

    h1 {
        font-size: 3rem;
    }

    height: 100%;

    div {
        display: flex;
        height: 90%;
    }
`

export const DeviceList = styled.ul`
    display: flex;
    flex-direction: row;
    gap: 20px;

    padding: 15px;
    background-color: white;
    border-radius: 10px;
    border: 1px solid #eeeeee;
    height: 100%;
    width: 100%;

    li {
        height: 150px;
    }
`