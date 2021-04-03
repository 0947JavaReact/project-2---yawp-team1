import axios from 'axios'
import { SET_USER, ADD_FOLLOWING, LOGIN_USER, LOGOUT_USER } from '../actions/types'

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
        const following = await axios.post("http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/following",{user_id: res.data.userId});
        let loggedInFollowing = following.data.map((user:any) => {
            return user.userId;
        });
        console.log(`In the loginUser action, list of following: ${loggedInFollowing}`);
        user = {
            username: res.data.username,
            id: res.data.userId,
            bio: res.data.bio,
            email: res.data.email,
            profilePic: res.data.picUrl,
            loggedInFollowing: loggedInFollowing,
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

export const updateFollowingList = (id:number) => (dispatch: any) => {
    console.log("In the update following action");
    return dispatch({
        type: ADD_FOLLOWING,
        payload: id
    });
}