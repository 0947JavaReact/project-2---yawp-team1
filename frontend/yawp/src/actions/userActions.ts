import { useRadioGroup } from '@material-ui/core'
import axios from 'axios'
import {ADD_USER,FETCH_USER,UPDATE_USER, LOGIN_USER} from '../actions/types'


export function fetchUser(username:string) {
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

export const loginUser = (obj:any) => async (dispatch:any) => {
    console.log("above the await");
    const res = await axios.post("http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/login", obj);
    const user = {
        username: res.data.username,
        id: res.data.userId,
        bio: res.data.bio,
        profilePic: res.data.picUrl,
        loggedIn: true
    }
    console.log(`Got the data: ${user}`);
    localStorage.setItem('username', res.data.username);
    console.log("After the request")
    return dispatch({
        type: LOGIN_USER,
        payload: user
    });
} 