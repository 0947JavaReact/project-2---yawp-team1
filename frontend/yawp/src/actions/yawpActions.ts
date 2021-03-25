import { ADD_YAWP, FETCH_YAWPS } from '../actions/types';

export function fetchYawps() {
    return function (dispatch: any) {
        const yawp1 = {
            id: 1,
            content: "I am a YAWP.",
            likes: 2
        };

        const yawp2 = {
            id: 2,
            content: "Why am I a YAWP?",
            likes: 22
        };

        const yawp3 = {
            id: 3,
            content: "Rawr. I am not a YAWP. But I am.",
            likes: 222
        };

        dispatch({
            type: FETCH_YAWPS,
            payload: [yawp1, yawp2, yawp3]
        })
    }
}