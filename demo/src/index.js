import React from 'react';
import { createRoot } from 'react-dom/client';
import {version, initTokenStyles, dsButton} from 'daisy';

const _ = initTokenStyles();
const Button = dsButton();

function App() {
    return <div>
        <h1>Hello, React!</h1>
        <p>version {version}</p>
        <div>
        <main>
        <section>
        <h2>design tokens</h2>
        <p class="ds-color-blue">should be blue (class)</p>
        <p class="ds-color-green">should be green (class)</p>
        </section>
        </main>
        // <Button></Button>
        </div>
        </div>;
}

const container = document.getElementById('app');
const root = createRoot(container);
root.render(<App />);
