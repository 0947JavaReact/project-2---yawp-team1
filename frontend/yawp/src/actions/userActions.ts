import {ADD_USER,FETCH_USER,UPDATE_USER, LOGIN_USER} from '../actions/types'


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

export function loginUser(){
    return function (dispatch:any){
        /* axios.get(...) */
        const u = {
            username:'',
            id:'1',
            bio:'',
            profile_pic:'',
            loggedIn: true
        }
        console.log("in login user");
        localStorage.setItem('id', u.id);
        dispatch({
            type:LOGIN_USER,
            payload: u,
        })
    }
}