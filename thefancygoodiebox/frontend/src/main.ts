import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from '../frontend/src/app/app.component';

bootstrapApplication(AppComponent, {
    providers: [
        importProvidersFrom(
            FormsModule,
            HttpClientModule
        )
    ]
});


