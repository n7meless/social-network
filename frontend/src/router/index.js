import {createRouter, createWebHistory} from 'vue-router'
import Login from "@/views/auth/Login";
import Register from "@/views/auth/Register";
import Forgot from "@/views/auth/Forgot";
import Reset from "@/views/auth/Reset";
import MainBody from "@/views/main/MainBody";
import NewsPage from "@/components/NewsPage";
import MessagePage from "@/components/MessagePage";
import SearchPage from "@/components/SearchPage";
import Account from "@/components/Account";
import MessagePanel from "@/components/MessagePanel";
import SearchInput from "@/components/SearchInput";

const routes = [

    {
        path: '/',
        name: 'MainBody',
        component: MainBody,
        children: [{
            path: 'news',
            component: NewsPage
        }, {
            path: '/msg',
            component: MessagePage,
            children: [{
                path: '/msg/:id',
                component: MessagePanel
            }
            ]
        },
            {
                path: '/search',
                component: SearchPage,
            }
        ]
    },
    {
        path: '/reset/:token',
        name: 'reset',
        component: Reset
    },
    {
        path: '/account/:id',
        name: 'account',
        component: Account
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/forgot',
        name: 'forgot',
        component: Forgot
    },
    {
        path: '/registration',
        name: 'registration',
        component: Register
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
