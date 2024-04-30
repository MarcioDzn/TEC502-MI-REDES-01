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

export const HeaderContainer = styled.div`
    height: fit-content !important;
    display: flex;
    justify-content: space-between;
    align-items: center;

    button {
        outline: none;
        border: 1px solid #eeeeff;
        padding: 8px 15px;
        background-color: white;
        cursor: pointer;
        border-radius: 0px 5px 5px 0px;

        transition: all 0.5s ease-in-out;
    }

    button:hover {
        background-color: lightgray;
    }

    input {
        padding: 8px;
        font-weight: bold;
        outline: none;
        border: 1px solid #eeeeff;
        background-color: white;
        border-radius: 5px 0px 0px 5px;
    }
`