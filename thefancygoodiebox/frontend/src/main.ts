import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http';  // ← Hinzufügen!


bootstrapApplication(AppComponent, {
    providers: [
        provideHttpClient()  // ← Hinzufügen!
        // deine anderen providers...
    ]
}).catch(err => console.error(err));


