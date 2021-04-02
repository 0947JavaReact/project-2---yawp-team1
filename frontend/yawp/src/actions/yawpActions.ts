import { ADD_YAWP, FETCH_USER_YAWPS, FETCH_FOLLOWING_YAWPS, CLEAR_YAWPS, LIKE_YAWP } from '../actions/types';
import axios from 'axios';

export const clearYawps = () => async (dispatch: any) => {
    return dispatch({
        type: CLEAR_YAWPS,
        payload: []
    });
};

export const fetchUserYawps = (userId: number) => async (dispatch: any) => {

    try {
        let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/yawps/uyawps/${userId}`);
        return dispatch({
            type: FETCH_USER_YAWPS,
            payload: res.data
        });

    }catch (e) {
        console.log("in the catch of fetch user yawps")
        return dispatch({
            type: FETCH_USER_YAWPS,
            payload: []
        })
    }

}

export const postYawp = (obj: any) => async (dispatch: any) => {
    try {
        let yawp = {
            message: obj.message,
            author_id: obj.authorId
        }
        await axios.post("http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/yawps/create", yawp);

        return dispatch({
            type: ADD_YAWP,
            payload: obj
        })
    }
    catch (e) {
        console.log("something bad happened");
    }
}

export const fetchFollowingPosts = (userId: number) => async (dispatch: any) => {

    try {
        let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/ufyawps/following/${userId}`);

        return dispatch({
            type: FETCH_FOLLOWING_YAWPS,
            payload: res.data
        });

    } catch (e) {
        return dispatch({
            type: FETCH_FOLLOWING_YAWPS,
            payload: []
        });
    }

}

export const likeYawp = (obj : any) => async (dispatch: any) => {

    try{

        let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/ufyawps/likeyawp/${obj.userId}/${obj.yawp.yawpId}`)

        let res1 = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/userid/${obj.userId}`)

        obj.yawp.likes.push(res1.data)


    return dispatch({

        type: LIKE_YAWP,
        payload: obj
    })
    }
    catch (e) {
        console.log(e);
    }
}

