package com.ejemplo;
import com.microsoft.playwright.options.WaitForSelectorState;

import com.microsoft.playwright.*;


public class PruebaLogin {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            // 1. Ir al login
            page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // 2. Esperar que se carguen los campos
            page.waitForSelector("input[name='username']");
            page.fill("input[name='username']", "Admin");
            page.fill("input[name='password']", "admin123");

            // 3. Clic en el boton de login
            page.click("button[type='submit']");

            // 4. Esperar a que aparezca algo que indique que se ha logueado
            page.waitForSelector("h6.oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module");

            // 5. Validacion
            String tituloDashboard = page.textContent("h6.oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module");
            System.out.println("Pagina despues de login: " + tituloDashboard);


            // 6. esperar y clickear time
            page.click("a[href='/web/index.php/time/viewTimeModule']");
            page.waitForURL("**/web/index.php/time/viewEmployeeTimesheet");

            // Confirmar que estas en la pagina correcta
            System.out.println("Pagina cargada: " + page.url());
            page.waitForTimeout(1000); // espera 3 segundos (1000 milisegundos)


            //primera prueba boton view del primer listado funcionando
            // contar botones
            Locator viewButtons = page.locator("button:has-text('View')");
            System.out.println("Total botones View encontrados: " + viewButtons.count());
            page.locator("button:has-text('View')").nth(1).click();
            page.waitForTimeout(1000); // espera 3 segundos (1000 milisegundos)


            //prueba 2 boton edit Funciono
            page.waitForSelector("button:has-text('Edit')");
            page.locator("button:has-text('Edit')").click();
            page.waitForTimeout(1000); // espera 3 segundos (1000 milisegundos)

            // prueba 3 boton cancel en el edit
            page.waitForSelector("button:has-text('Cancel')");
            page.locator("button:has-text('Cancel')").click();
            page.waitForTimeout(1000); // espera 3 segundos (1000 milisegundos)




            // escenario 2
            // Paso 1. Click en "Project Info"
            page.locator("li:has-text('Project Info')").click();

            // Paso 2. Esperar y hacer clic en "Projects"
            page.waitForSelector("a:has-text('Projects')");
            page.locator("a:has-text('Projects')").click();

            // Paso 3. Esperar a que cargue la URL de proyectos
            page.waitForURL("**/time/viewProjects");

            // Paso 4. Espera adicional para renderizado
            page.waitForTimeout(3000);

            // Paso 5. Clic en boton de editar (icono lápiz)
            page.locator("button:has(i.oxd-icon.bi-pencil-fill)").first().click();

            // Paso 6. Clic en boton "Add"
            page.waitForSelector("button:has(i.oxd-icon.bi-plus)");
            page.locator("button:has-text('Add')").nth(2).click();

            // Paso 7. Esperar que el modal este visible
            Locator modal = page.locator("div.orangehrm-dialog-modal");
            modal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            // Input dentro del modal
            Locator inputNuevo = modal.locator("input.oxd-input--active").first();
            inputNuevo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            // Hacer doble click para asegurar foco y seleccion
            inputNuevo.dblclick();
            page.waitForTimeout(2000);
            page.locator("input.oxd-input--focus").first().fill("prueba 2");
            page.waitForTimeout(3000);

            // Paso 9. Click en boton "Save" dentro del modal
            modal.locator("button:has-text('Save')").click();

            // Paso 10. Click en checkbox correspondiente
            page.locator("span.oxd-checkbox-input").first().click();

            // Paso 13. Esperar 3 segundos
            page.waitForTimeout(3000);
            // Paso 11. Click en botón "Delete Selected"
            page.locator("button:has-text('Delete Selected')").click();

            // Paso 13. Esperar 3 segundos
            page.waitForTimeout(3000);
            // Paso 12. Click en boton de confirmacion "Yes, Delete"
            page.locator("button:has-text('Yes, Delete')").click();

            // Paso 13. Esperar 3 segundos
            page.waitForTimeout(3000);









            // escenario 3 
                  // --- Paso 1: Click en Attendance (abre dropdown) ---
            page.waitForSelector("span.oxd-topbar-body-nav-tab-item:has-text('Attendance')").click();

            // --- Paso 2: Click en "Employee Records" ---
            page.waitForSelector("a.oxd-topbar-body-nav-tab-link:has-text('Employee Records')").click();

            // --- Paso 3: Esperar a que cargue Employee Records ---
            page.waitForURL("**/attendance/viewAttendanceRecord");

            // --- Paso 4: Click en boton "View" (primero de la lista) ---
            page.locator("button:has-text('View')").nth(1).click();

            // --- Paso 5: Esperar que cargue la nueva pagina ---
            page.waitForLoadState();

            // --- Paso 6: Click en boton "Add" ---
            page.waitForSelector("button:has-text('Add')").click();

            // --- Paso 7: Esperar que cargue el formulario ---
            page.waitForLoadState();

            // --- Paso 8: Escribir texto en textarea ---
            Locator textarea = page.locator("textarea[placeholder='Type here']");
            textarea.fill("Este es un escenario de prueba");

            // --- Paso 9: Click en boton "In" ---
            if (page.locator("button:has-text('In')").isVisible()) {
                page.locator("button:has-text('In')").click();
                System.out.println("Clic en 'In'");
            } else if (page.locator("button:has-text('Out')").isVisible()) {
                page.locator("button:has-text('Out')").click();
                System.out.println("Clic en 'Out'");
            } else {
                System.out.println("No se encontro boton 'In' ni 'Out'");
            }
            
            // --- Paso 10: Esperar hasta que se muestre la fila con el texto ---
            try {
                // Esperar explicitamente hasta que aparezca la fila deseada (timeout: 10s)
                page.waitForSelector(
                    "div.oxd-table-row.oxd-table-row--with-border[role='row']:has-text('Este es un escenario de prueba')",
                    new Page.WaitForSelectorOptions().setTimeout(10000)
                );

                System.out.println("El registro esperado existe en la tabla.");
            } catch (PlaywrightException e) {
                System.out.println("No se encontro el registro.");
            }
            
        }
    }
}
