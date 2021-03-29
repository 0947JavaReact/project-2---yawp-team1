import { ADD_YAWP, FETCH_USER_YAWPS, FETCH_FOLLOWING_YAWPS } from '../actions/types';
import img1 from '../img/profile-picture-default.jpeg';
import { Yawp } from '../components/CreateYawp/CreateYawp';
import axios from 'axios';

export function fetchUserYawps(username:string) {
    return function (dispatch: any) {
        const yawp1 = {
            id: 1,
            username: "Bob_User",
            content: "I am a YAWP.",
            likes: 2,
            profilePic: img1
        };

        const yawp2 = {
            id: 2,
            username: "Balut1",
            content: "Why am I a YAWP?",
            likes: 22,
            profilePic: img1
        };

        const yawp3 = {
            id: 3,
            username: "BuilderOfBobs2",
            content: "Rawr. I am not a YAWP. But I am.",
            likes: 222,
            profilePic: img1
        };

        dispatch({
            type: FETCH_USER_YAWPS,
            payload: [yawp1, yawp2, yawp3]
        })
    }
}

export const postYawp = (obj:any) => async (dispatch:any) => {
        try{
            let res = await axios.post("http://localhost:9025/yawps/create", obj);
            console.log(res.data);
        }
        catch(e){
            console.log("something bad happened");
        }
}

export const fetchFollowingPosts = (userId:number) => async (dispatch:any) => {

    try{
        let res = await axios.get(`http://localhost:9025/ufyawps/following/${userId}`);
        console.log(res.data);
        
        dispatch({
            type: FETCH_FOLLOWING_YAWPS,
            payload: res.data
        });
        
    }catch(e){
        console.log("something went wrong");
    }
    
}