import axios from 'axios'
import {SET_USER, UPDATE_USER, LOGIN_USER, LOGOUT_USER} from '../actions/types'


export function setUser(user:any) {
    return function (dispatch:any) {
        dispatch({
            type:SET_USER,
            payload:user,
        })
    }
}

export const loginUser = (obj:any) => async (dispatch:any) => {
    let user;
    try{
        const res = await axios.post("http://localhost:9025/users/login", obj);
        user = {
            username: res.data.username,
            id: res.data.userId,
            bio: res.data.bio,
            profilePic: res.data.picUrl,
            loggedIn: true,
            loginAttempt: 'success'
        }
        localStorage.setItem("username", res.data.username);
        localStorage.setItem("id", res.data.userId);
        localStorage.setItem("profilPic", res.data.picUrl);
        return dispatch({
            type: LOGIN_USER,
            payload: user
        });
    } catch(e){
        user = {
            username: null,
            id: -1,
            bio: null,
            profilePic: null,
            loggedIn: false,
            loginAttempt: 'invalid'
        }
        return dispatch({
            type: LOGIN_USER,
            payload: user
        });
    }
}

export const logout = () => (dispatch:any) => {
    localStorage.clear();
    return dispatch({
        type: LOGOUT_USER,
        payload: {
            username: null,
            id: -1,
            bio: null,
            profilePic: null,
            loggedIn: false
        }
    });
}