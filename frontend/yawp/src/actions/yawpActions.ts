import { ADD_YAWP, FETCH_YAWPS } from '../actions/types';
import img1 from '../img/profile-picture-default.jpeg';

export function fetchYawps() {
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
            type: FETCH_YAWPS,
            payload: [yawp1, yawp2, yawp3]
        })
    }
}