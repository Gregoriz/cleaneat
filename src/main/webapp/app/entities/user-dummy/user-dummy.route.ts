import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { UserDummyComponent } from './user-dummy.component';
import { UserDummyDetailComponent } from './user-dummy-detail.component';
import { UserDummyPopupComponent } from './user-dummy-dialog.component';
import { UserDummyDeletePopupComponent } from './user-dummy-delete-dialog.component';

import { Principal } from '../../shared';

export const userDummyRoute: Routes = [
    {
        path: 'user-dummy',
        component: UserDummyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDummies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-dummy/:id',
        component: UserDummyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDummies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userDummyPopupRoute: Routes = [
    {
        path: 'user-dummy-new',
        component: UserDummyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDummies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-dummy/:id/edit',
        component: UserDummyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDummies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-dummy/:id/delete',
        component: UserDummyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDummies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
