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

    console.log("In login user action")

    try{
    const res = await axios.post("http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/user/login", obj)
    console.log(res.data)
    }
    catch(e)
    {
        console.log(e)
    }


    // if(res.status !== 200)
    // {
    //     const u = {
    //              username: null,
    //              id: null,
    //              bio: null,
    //              profile_pic: null,
    //              loggedIn: false
    //          }

    //          dispatch({
    //              type:LOGIN_USER,
    //              payload: u
    //          })
    // }
}

// export function loginUser(obj:any){
//     return function (dispatch:any){
        
//         const res = await axios.post("", obj)
//         axios.

//         // const u = {
//         //     username:'bob',
//         //     id:'1',
//         //     bio:'',
//         //     profile_pic:'',
//         //     loggedIn: true
//         // }
//         console.log("in login user");
//         localStorage.setItem('username', u.username);
//         dispatch({
//             type:LOGIN_USER,
//             payload: u,
//         })
//     }
// }