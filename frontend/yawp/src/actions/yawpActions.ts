import { ADD_YAWP, FETCH_USER_YAWPS, FETCH_FOLLOWING_YAWPS } from '../actions/types';
import axios from 'axios';

export const fetchUserYawps = (userId:number) => async (dispatch:any) => {

    try{
        let res = await axios.get(`http://localhost:9025/yawps/uyawps/${userId}`);
        return dispatch({
            type: FETCH_USER_YAWPS,
            payload: res.data
        });
        
    }catch(e){
    }
    
}

export const postYawp = (obj:any) => async (dispatch:any) => {
        try{
            let yawp = {
                message: obj.message,
                author_id: obj.authorId
            }
            await axios.post("http://localhost:9025/yawps/create", yawp);
            
            return dispatch({
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
        
        return dispatch({
            type: FETCH_FOLLOWING_YAWPS,
            payload: res.data
        });
        
    }catch(e){
        console.log("something went wrong");
    }
    
}