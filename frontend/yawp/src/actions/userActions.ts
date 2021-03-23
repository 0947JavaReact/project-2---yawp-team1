import {ADD_USER,FETCH_USER,UPDATE_USER} from '../actions/types'


export function fetchUser() {
    return function (dispatch:any) {
        const u = {
            username:'bob',
            id:'1',
            password:'pass',
            bio:'im bob',
            profile_pic:'pic',
            email:'email@email.com'
        }
        dispatch({
            type:FETCH_USER,
            payload:u,
        })
    }
}