import axios from 'axios'
import { SET_USER, UPDATE_USER, LOGIN_USER, LOGOUT_USER } from '../actions/types'

export const setUser = (user:any) => async (dispatch:any) => {
    return dispatch({
            type: SET_USER,
            payload: user,
        })
}

export const loginUser = (obj: any) => async (dispatch: any) => {
    let user;
    try {
        const res = await axios.post("http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/login", obj);
        user = {
            username: res.data.username,
            id: res.data.userId,
            bio: res.data.bio,
            email: res.data.email,
            profilePic: res.data.picUrl,
            loggedIn: true,
            loginAttempt: 'success'
        }
        localStorage.setItem("id", res.data.userId);
        return dispatch({
            type: LOGIN_USER,
            payload: user
        });
    } catch (e) {
        user = {
            username: null,
            id: -1,
            bio: null,
            email: null,
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

export const logout = () => (dispatch: any) => {
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