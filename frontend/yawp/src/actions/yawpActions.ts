import { ADD_YAWP, FETCH_USER_YAWPS, FETCH_FOLLOWING_YAWPS } from '../actions/types';
import img1 from '../img/profile-picture-default.jpeg';
import { Yawp } from '../components/CreateYawp/CreateYawp';
import axios from 'axios';
import userEvent from '@testing-library/user-event';

export const fetchUserYawps = (userId:number) => async (dispatch:any) => {

    try{
        console.log(userId);
        let res = await axios.get(`http://localhost:9025/yawps/uyawps/${userId}`);
        console.log(res.data);
        
        dispatch({
            type: FETCH_USER_YAWPS,
            payload: res.data
        });
        
    }catch(e){
        console.log("something went wrong");
    }
    
}

export const postYawp = (obj:any) => async (dispatch:any) => {
        try{
            let yawp = {
                message: obj.message,
                author_id: obj.authorId
            }
            let res = await axios.post("http://localhost:9025/yawps/create", yawp);
            console.log(res.data);
            dispatch({
                type: ADD_YAWP,
                payload: obj
            })
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